import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/unidad-academica">
        <Translate contentKey="global.menu.entities.unidadAcademica" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/subunidad-academica">
        <Translate contentKey="global.menu.entities.subunidadAcademica" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/programa-academico">
        <Translate contentKey="global.menu.entities.programaAcademico" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
