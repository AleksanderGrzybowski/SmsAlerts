import React from 'react';
import { Nav, Navbar } from 'react-bootstrap';
import AccountBalance from './AccountBalance';
import Menu from './Menu';

function AppToolbar(props) {
  return (
    <Navbar bg="light" variant="light">
      <Navbar.Brand href="#home">
        <i className="fa fa-train" style={{paddingRight: 10}}/>
        SmsAlerts
      </Navbar.Brand>
      <Nav className="mr-auto">
        <Menu {...props} />
      </Nav>
      <AccountBalance/>
    </Navbar>
  );
}

export default AppToolbar;
