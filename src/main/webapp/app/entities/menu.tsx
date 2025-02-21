import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/bank-person">
        <Translate contentKey="global.menu.entities.bankPerson" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-customer">
        <Translate contentKey="global.menu.entities.bankCustomer" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-teller">
        <Translate contentKey="global.menu.entities.bankTeller" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-account">
        <Translate contentKey="global.menu.entities.bankAccount" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/bank-transaction">
        <Translate contentKey="global.menu.entities.bankTransaction" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
