import React from 'react';
import AppBar from '@material-ui/core/AppBar/AppBar';
import Toolbar from '@material-ui/core/Toolbar/Toolbar';
import Typography from '@material-ui/core/Typography/Typography';
import TrainIcon from '@material-ui/icons/Train';
import IconButton from '@material-ui/core/IconButton/IconButton';

function AppToolbar() {
  return (
    <AppBar position="static">
      <Toolbar>
        <IconButton color="inherit" aria-label="Menu">
          <TrainIcon/>
        </IconButton>
        <Typography variant="title" color="inherit">
          SmsAlerts
        </Typography>
      </Toolbar>
    </AppBar>
  );
}

export default AppToolbar;
