import React from 'react';
import AccountBalance from '../AccountBalance';
import { Nav, Navbar } from 'react-bootstrap';
import Menu from '../Menu';

function AppToolbar(props) {
  return (
    <Navbar bg="light" variant="light">
      <Navbar.Brand href="#home">(train icon) SmsAlerts</Navbar.Brand>
      <Nav className="mr-auto">
        <Menu {...props} />
      </Nav>
      <AccountBalance/>
    </Navbar>
  );
}

export default AppToolbar;
