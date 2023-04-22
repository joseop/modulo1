import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubunidadAcademica } from 'app/shared/model/subunidad-academica.model';
import { getEntities as getSubunidadAcademicas } from 'app/entities/subunidad-academica/subunidad-academica.reducer';
import { IProgramaAcademico } from 'app/shared/model/programa-academico.model';
import { getEntity, updateEntity, createEntity, reset } from './programa-academico.reducer';

export const ProgramaAcademicoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const subunidadAcademicas = useAppSelector(state => state.subunidadAcademica.entities);
  const programaAcademicoEntity = useAppSelector(state => state.programaAcademico.entity);
  const loading = useAppSelector(state => state.programaAcademico.loading);
  const updating = useAppSelector(state => state.programaAcademico.updating);
  const updateSuccess = useAppSelector(state => state.programaAcademico.updateSuccess);

  const handleClose = () => {
    navigate('/programa-academico' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSubunidadAcademicas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...programaAcademicoEntity,
      ...values,
      subunidadAcademica: subunidadAcademicas.find(it => it.id.toString() === values.subunidadAcademica.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...programaAcademicoEntity,
          subunidadAcademica: programaAcademicoEntity?.subunidadAcademica?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="modulo1App.programaAcademico.home.createOrEditLabel" data-cy="ProgramaAcademicoCreateUpdateHeading">
            <Translate contentKey="modulo1App.programaAcademico.home.createOrEditLabel">Create or edit a ProgramaAcademico</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="programa-academico-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('modulo1App.programaAcademico.codigoPrograma')}
                id="programa-academico-codigoPrograma"
                name="codigoPrograma"
                data-cy="codigoPrograma"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.programaAcademico.nombrePrograma')}
                id="programa-academico-nombrePrograma"
                name="nombrePrograma"
                data-cy="nombrePrograma"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.programaAcademico.codigoSnies')}
                id="programa-academico-codigoSnies"
                name="codigoSnies"
                data-cy="codigoSnies"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.programaAcademico.nombreEncargado')}
                id="programa-academico-nombreEncargado"
                name="nombreEncargado"
                data-cy="nombreEncargado"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.programaAcademico.tipoPrograma')}
                id="programa-academico-tipoPrograma"
                name="tipoPrograma"
                data-cy="tipoPrograma"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.programaAcademico.modalidadPrograma')}
                id="programa-academico-modalidadPrograma"
                name="modalidadPrograma"
                data-cy="modalidadPrograma"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.programaAcademico.contactosAyuda')}
                id="programa-academico-contactosAyuda"
                name="contactosAyuda"
                data-cy="contactosAyuda"
                type="text"
              />
              <ValidatedField
                id="programa-academico-subunidadAcademica"
                name="subunidadAcademica"
                data-cy="subunidadAcademica"
                label={translate('modulo1App.programaAcademico.subunidadAcademica')}
                type="select"
              >
                <option value="" key="0" />
                {subunidadAcademicas
                  ? subunidadAcademicas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/programa-academico" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ProgramaAcademicoUpdate;
