import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subunidad-academica.reducer';

export const SubunidadAcademicaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subunidadAcademicaEntity = useAppSelector(state => state.subunidadAcademica.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subunidadAcademicaDetailsHeading">
          <Translate contentKey="modulo1App.subunidadAcademica.detail.title">SubunidadAcademica</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{subunidadAcademicaEntity.id}</dd>
          <dt>
            <span id="codigoSubunidad">
              <Translate contentKey="modulo1App.subunidadAcademica.codigoSubunidad">Codigo Subunidad</Translate>
            </span>
          </dt>
          <dd>{subunidadAcademicaEntity.codigoSubunidad}</dd>
          <dt>
            <span id="nombreSubunidad">
              <Translate contentKey="modulo1App.subunidadAcademica.nombreSubunidad">Nombre Subunidad</Translate>
            </span>
          </dt>
          <dd>{subunidadAcademicaEntity.nombreSubunidad}</dd>
          <dt>
            <span id="tipoSubunidad">
              <Translate contentKey="modulo1App.subunidadAcademica.tipoSubunidad">Tipo Subunidad</Translate>
            </span>
          </dt>
          <dd>{subunidadAcademicaEntity.tipoSubunidad}</dd>
          <dt>
            <span id="nombreJefe">
              <Translate contentKey="modulo1App.subunidadAcademica.nombreJefe">Nombre Jefe</Translate>
            </span>
          </dt>
          <dd>{subunidadAcademicaEntity.nombreJefe}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="modulo1App.subunidadAcademica.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{subunidadAcademicaEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="modulo1App.subunidadAcademica.unidadAcademica">Unidad Academica</Translate>
          </dt>
          <dd>{subunidadAcademicaEntity.unidadAcademica ? subunidadAcademicaEntity.unidadAcademica.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subunidad-academica" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subunidad-academica/${subunidadAcademicaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubunidadAcademicaDetail;
