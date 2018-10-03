import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Divider from '@material-ui/core/Divider';
import EmailIcon from '@material-ui/icons/Email';
import WarningIcon from '@material-ui/icons/Warning';
import InfoIcon from '@material-ui/icons/Info';

const styles = theme => ({
  root: {
    width: '50%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
});

function Menu(props) {
  const {classes} = props;
  return (
    <div className={classes.root}>
      <List component="nav">
        <ListItem button>
          <ListItemIcon>
            <WarningIcon/>
          </ListItemIcon>
          <ListItemText primary="Last alerts"/>
        </ListItem>

        <ListItem button>
          <ListItemIcon>
            <EmailIcon/>
          </ListItemIcon>
          <ListItemText primary="Sent messages"/>
        </ListItem>

        <Divider/>

        <ListItem button>
          <ListItemIcon>
            <InfoIcon/>
          </ListItemIcon>
          <ListItemText primary="About"/>
        </ListItem>

      </List>
    </div>
  );
}

export default withStyles(styles)(Menu);