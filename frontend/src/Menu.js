import React from 'react';
import { Nav } from 'react-bootstrap';

function Menu(props) {
  const makeMenuItem = (name, text, icon) =>
    <Nav.Link
      key={name}
      selected={props.selectedMenuItem === name}
      onClick={() => props.onMenuItemClick(name)}
    >
      <span>
        {icon}
      </span>
      <p>{text}</p>
    </Nav.Link>;

  return [
    makeMenuItem('alerts', 'Last alerts', 'warning'),
    makeMenuItem('messages', 'Sent messages', 'email'),
    makeMenuItem('about', 'About', 'about'),
  ];
}

export default Menu;