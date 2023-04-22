import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUnidadAcademica } from 'app/shared/model/unidad-academica.model';
import { getEntity, updateEntity, createEntity, reset } from './unidad-academica.reducer';

export const UnidadAcademicaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const unidadAcademicaEntity = useAppSelector(state => state.unidadAcademica.entity);
  const loading = useAppSelector(state => state.unidadAcademica.loading);
  const updating = useAppSelector(state => state.unidadAcademica.updating);
  const updateSuccess = useAppSelector(state => state.unidadAcademica.updateSuccess);

  const handleClose = () => {
    navigate('/unidad-academica');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...unidadAcademicaEntity,
      ...values,
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
          ...unidadAcademicaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="modulo1App.unidadAcademica.home.createOrEditLabel" data-cy="UnidadAcademicaCreateUpdateHeading">
            <Translate contentKey="modulo1App.unidadAcademica.home.createOrEditLabel">Create or edit a UnidadAcademica</Translate>
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
                  id="unidad-academica-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('modulo1App.unidadAcademica.codigoUnidad')}
                id="unidad-academica-codigoUnidad"
                name="codigoUnidad"
                data-cy="codigoUnidad"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.unidadAcademica.nombreUnidad')}
                id="unidad-academica-nombreUnidad"
                name="nombreUnidad"
                data-cy="nombreUnidad"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.unidadAcademica.tipoUnidad')}
                id="unidad-academica-tipoUnidad"
                name="tipoUnidad"
                data-cy="tipoUnidad"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.unidadAcademica.nombreDecano')}
                id="unidad-academica-nombreDecano"
                name="nombreDecano"
                data-cy="nombreDecano"
                type="text"
              />
              <ValidatedField
                label={translate('modulo1App.unidadAcademica.descripcion')}
                id="unidad-academica-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/unidad-academica" replace color="info">
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

export default UnidadAcademicaUpdate;
