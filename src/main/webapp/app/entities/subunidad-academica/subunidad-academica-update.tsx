import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUnidadAcademica } from 'app/shared/model/unidad-academica.model';
import { getEntities as getUnidadAcademicas } from 'app/entities/unidad-academica/unidad-academica.reducer';
import { ISubunidadAcademica } from 'app/shared/model/subunidad-academica.model';
import { getEntity, updateEntity, createEntity, reset } from './subunidad-academica.reducer';

export const SubunidadAcademicaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const unidadAcademicas = useAppSelector(state => state.unidadAcademica.entities);
  const subunidadAcademicaEntity = useAppSelector(state => state.subunidadAcademica.entity);
  const loading = useAppSelector(state => state.subunidadAcademica.loading);
  const updating = useAppSelector(state => state.subunidadAcademica.updating);
  const updateSuccess = useAppSelector(state => state.subunidadAcademica.updateSuccess);

  const handleClose = () => {
    navigate('/subunidad-academica');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUnidadAcademicas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...subunidadAcademicaEntity,
      ...values,
      unidadAcademica: unidadAcademicas.find(it => it.id.toString() === values.unidadAcademica.toString()),
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
          ...subunidadAcademicaEntity,
          unidadAcademica: subunidadAcademicaEntity?.unidadAcademica?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="modulo1App.subunidadAcademica.home.createOrEditLabel" data-cy="SubunidadAcademicaCreateUpdateHeading">
            <Translate contentKey="modulo1App.subunidadAcademica.home.createOrEditLabel">Create or edit a SubunidadAcademica</Translate>
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
                  id="subunidad-academica-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('modulo1App.subunidadAcademica.codigoSubunidad')}
                id="subunidad-academica-codigoSubunidad"
                name="codigoSubunidad"
                data-cy="codigoSubunidad"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.subunidadAcademica.nombreSubunidad')}
                id="subunidad-academica-nombreSubunidad"
                name="nombreSubunidad"
                data-cy="nombreSubunidad"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.subunidadAcademica.tipoSubunidad')}
                id="subunidad-academica-tipoSubunidad"
                name="tipoSubunidad"
                data-cy="tipoSubunidad"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.subunidadAcademica.nombreJefe')}
                id="subunidad-academica-nombreJefe"
                name="nombreJefe"
                data-cy="nombreJefe"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.subunidadAcademica.descripcion')}
                id="subunidad-academica-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                id="subunidad-academica-unidadAcademica"
                name="unidadAcademica"
                data-cy="unidadAcademica"
                label={translate('modulo1App.subunidadAcademica.unidadAcademica')}
                type="select"
              >
                <option value="" key="0" />
                {unidadAcademicas
                  ? unidadAcademicas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subunidad-academica" replace color="info">
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

export default SubunidadAcademicaUpdate;
