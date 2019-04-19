import React from 'react';

function Menu(props) {
  const makeMenuItem = (name, text, icon) =>
    <span
      key={name}
      button
      selected={props.selectedMenuItem === name}
      onClick={() => props.onMenuItemClick(name)}
    >
      <span>
        {icon}
      </span>
      <p>{text}</p>
    </span>;

  return [
    makeMenuItem('alerts', 'Last alerts', 'warning'),
    makeMenuItem('messages', 'Sent messages', 'email'),
    makeMenuItem('about', 'About', 'about'),
  ];
}

export default Menu;