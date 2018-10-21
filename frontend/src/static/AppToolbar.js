import React from 'react';
import AppBar from '@material-ui/core/AppBar/AppBar';
import Toolbar from '@material-ui/core/Toolbar/Toolbar';
import Typography from '@material-ui/core/Typography/Typography';
import TrainIcon from '@material-ui/icons/Train';
import IconButton from '@material-ui/core/IconButton/IconButton';
import { withStyles } from '@material-ui/core/styles';
import AccountBalance from '../AccountBalance';

const styles = theme => ({
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  grow: {
    flexGrow: 1,
  },
});

function AppToolbar({classes}) {
  return (
    <AppBar position="absolute" className={classes.appBar}>
      <Toolbar>
        <IconButton color="inherit" aria-label="Menu">
          <TrainIcon/>
        </IconButton>
        <Typography variant="title" color="inherit" className={classes.grow}>
          SmsAlerts
        </Typography>
        <Typography variant="title" color="inherit">
          <AccountBalance/>
        </Typography>
      </Toolbar>
    </AppBar>
  );
}

export default withStyles(styles)(AppToolbar);
