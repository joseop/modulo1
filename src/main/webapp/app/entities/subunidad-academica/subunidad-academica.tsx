import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubunidadAcademica } from 'app/shared/model/subunidad-academica.model';
import { getEntities, reset } from './subunidad-academica.reducer';

export const SubunidadAcademica = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const subunidadAcademicaList = useAppSelector(state => state.subunidadAcademica.entities);
  const loading = useAppSelector(state => state.subunidadAcademica.loading);
  const totalItems = useAppSelector(state => state.subunidadAcademica.totalItems);
  const links = useAppSelector(state => state.subunidadAcademica.links);
  const entity = useAppSelector(state => state.subunidadAcademica.entity);
  const updateSuccess = useAppSelector(state => state.subunidadAcademica.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="subunidad-academica-heading" data-cy="SubunidadAcademicaHeading">
        <Translate contentKey="modulo1App.subunidadAcademica.home.title">Subunidad Academicas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="modulo1App.subunidadAcademica.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/subunidad-academica/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="modulo1App.subunidadAcademica.home.createLabel">Create new Subunidad Academica</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={subunidadAcademicaList ? subunidadAcademicaList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {subunidadAcademicaList && subunidadAcademicaList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="modulo1App.subunidadAcademica.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigoSubunidad')}>
                    <Translate contentKey="modulo1App.subunidadAcademica.codigoSubunidad">Codigo Subunidad</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombreSubunidad')}>
                    <Translate contentKey="modulo1App.subunidadAcademica.nombreSubunidad">Nombre Subunidad</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tipoSubunidad')}>
                    <Translate contentKey="modulo1App.subunidadAcademica.tipoSubunidad">Tipo Subunidad</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('nombreJefe')}>
                    <Translate contentKey="modulo1App.subunidadAcademica.nombreJefe">Nombre Jefe</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="modulo1App.subunidadAcademica.descripcion">Descripcion</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="modulo1App.subunidadAcademica.unidadAcademica">Unidad Academica</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {subunidadAcademicaList.map((subunidadAcademica, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/subunidad-academica/${subunidadAcademica.id}`} color="link" size="sm">
                        {subunidadAcademica.id}
                      </Button>
                    </td>
                    <td>{subunidadAcademica.codigoSubunidad}</td>
                    <td>{subunidadAcademica.nombreSubunidad}</td>
                    <td>{subunidadAcademica.tipoSubunidad}</td>
                    <td>{subunidadAcademica.nombreJefe}</td>
                    <td>{subunidadAcademica.descripcion}</td>
                    <td>
                      {subunidadAcademica.unidadAcademica ? (
                        <Link to={`/unidad-academica/${subunidadAcademica.unidadAcademica.id}`}>
                          {subunidadAcademica.unidadAcademica.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/subunidad-academica/${subunidadAcademica.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/subunidad-academica/${subunidadAcademica.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/subunidad-academica/${subunidadAcademica.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="modulo1App.subunidadAcademica.home.notFound">No Subunidad Academicas found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default SubunidadAcademica;
