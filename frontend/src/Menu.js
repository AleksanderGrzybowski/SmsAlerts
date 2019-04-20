import React from 'react';
import { Nav } from 'react-bootstrap';

function Menu(props) {
  const makeMenuItem = (name, text, icon) =>
    <Nav.Item
      key={name}
      selected={props.selectedMenuItem === name}
      onClick={() => props.onMenuItemClick(name)}
      style={{cursor: 'pointer', marginRight: 20, color: props.selectedMenuItem === name ? 'black' : 'gray'}}
    >
      <span style={{marginRight: 5}}>
        <i className={'fa fa-' + icon}/>
      </span>
      <span>{text}</span>
    </Nav.Item>;

  return [
    makeMenuItem('alerts', 'Last alerts', 'warning'),
    makeMenuItem('messages', 'Sent messages', 'envelope'),
    makeMenuItem('about', 'About', 'info')
  ];
}

export default Menu;