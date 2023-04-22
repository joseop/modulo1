import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './unidad-academica.reducer';

export const UnidadAcademicaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const unidadAcademicaEntity = useAppSelector(state => state.unidadAcademica.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="unidadAcademicaDetailsHeading">
          <Translate contentKey="modulo1App.unidadAcademica.detail.title">UnidadAcademica</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{unidadAcademicaEntity.id}</dd>
          <dt>
            <span id="codigoUnidad">
              <Translate contentKey="modulo1App.unidadAcademica.codigoUnidad">Codigo Unidad</Translate>
            </span>
          </dt>
          <dd>{unidadAcademicaEntity.codigoUnidad}</dd>
          <dt>
            <span id="nombreUnidad">
              <Translate contentKey="modulo1App.unidadAcademica.nombreUnidad">Nombre Unidad</Translate>
            </span>
          </dt>
          <dd>{unidadAcademicaEntity.nombreUnidad}</dd>
          <dt>
            <span id="tipoUnidad">
              <Translate contentKey="modulo1App.unidadAcademica.tipoUnidad">Tipo Unidad</Translate>
            </span>
          </dt>
          <dd>{unidadAcademicaEntity.tipoUnidad}</dd>
          <dt>
            <span id="nombreDecano">
              <Translate contentKey="modulo1App.unidadAcademica.nombreDecano">Nombre Decano</Translate>
            </span>
          </dt>
          <dd>{unidadAcademicaEntity.nombreDecano}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="modulo1App.unidadAcademica.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{unidadAcademicaEntity.descripcion}</dd>
        </dl>
        <Button tag={Link} to="/unidad-academica" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/unidad-academica/${unidadAcademicaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UnidadAcademicaDetail;
