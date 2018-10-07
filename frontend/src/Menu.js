import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import EmailIcon from '@material-ui/icons/Email';
import WarningIcon from '@material-ui/icons/Warning';
import InfoIcon from '@material-ui/icons/Info';
import Divider from '@material-ui/core/Divider/Divider';

const styles = theme => ({
  root: {
    width: '50%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
});

function Menu(props) {
  const makeMenuItem = (name, text, icon) => {
    return (
      <ListItem
        button
        selected={props.selectedMenuItem === name}
        onClick={() => props.onMenuItemClick(name)}
      >
        <ListItemIcon>
          {icon}
        </ListItemIcon>
        <ListItemText primary={text}/>
      </ListItem>
    );
  };

  return (
   [
      makeMenuItem('alerts', 'Last alerts', <WarningIcon/>),
      makeMenuItem('messages', 'Sent messages', <EmailIcon/>),
      <Divider/>,
      makeMenuItem('about', 'About', <InfoIcon/>),
   ]
  );
}

export default withStyles(styles)(Menu);