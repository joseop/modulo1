import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './programa-academico.reducer';

export const ProgramaAcademicoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const programaAcademicoEntity = useAppSelector(state => state.programaAcademico.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="programaAcademicoDetailsHeading">
          <Translate contentKey="modulo1App.programaAcademico.detail.title">ProgramaAcademico</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.id}</dd>
          <dt>
            <span id="codigoPrograma">
              <Translate contentKey="modulo1App.programaAcademico.codigoPrograma">Codigo Programa</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.codigoPrograma}</dd>
          <dt>
            <span id="nombrePrograma">
              <Translate contentKey="modulo1App.programaAcademico.nombrePrograma">Nombre Programa</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.nombrePrograma}</dd>
          <dt>
            <span id="codigoSnies">
              <Translate contentKey="modulo1App.programaAcademico.codigoSnies">Codigo Snies</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.codigoSnies}</dd>
          <dt>
            <span id="nombreEncargado">
              <Translate contentKey="modulo1App.programaAcademico.nombreEncargado">Nombre Encargado</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.nombreEncargado}</dd>
          <dt>
            <span id="tipoPrograma">
              <Translate contentKey="modulo1App.programaAcademico.tipoPrograma">Tipo Programa</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.tipoPrograma}</dd>
          <dt>
            <span id="modalidadPrograma">
              <Translate contentKey="modulo1App.programaAcademico.modalidadPrograma">Modalidad Programa</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.modalidadPrograma}</dd>
          <dt>
            <span id="contactosAyuda">
              <Translate contentKey="modulo1App.programaAcademico.contactosAyuda">Contactos Ayuda</Translate>
            </span>
          </dt>
          <dd>{programaAcademicoEntity.contactosAyuda}</dd>
          <dt>
            <Translate contentKey="modulo1App.programaAcademico.subunidadAcademica">Subunidad Academica</Translate>
          </dt>
          <dd>{programaAcademicoEntity.subunidadAcademica ? programaAcademicoEntity.subunidadAcademica.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/programa-academico" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/programa-academico/${programaAcademicoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProgramaAcademicoDetail;
