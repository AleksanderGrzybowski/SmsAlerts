import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import EntriesTable from './EntriesTable';
import MessagesTable from './MessagesTable';
import IconButton from '@material-ui/core/IconButton/IconButton';

import TrainIcon from '@material-ui/icons/Train';
import Menu from './Menu';
import Spinner from './static/Spinner';
import ServerError from './static/ServerError';
import axios from 'axios';

const drawerWidth = 240;

const styles = theme => ({
  root: {
    flexGrow: 1,
    height: 900,
    zIndex: 1,
    overflow: 'hidden',
    position: 'relative',
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  drawerPaper: {
    position: 'relative',
    width: drawerWidth,
  },
  content: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.default,
    padding: theme.spacing.unit * 3,
    minWidth: 0, // So the Typography noWrap works
  },
  toolbar: theme.mixins.toolbar,
});

class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      backendStatus: 'pending',
      selectedMenuItem: 'alerts'
    };
  }

  componentDidMount() {
    axios.get("/health")
      .then(() => this.setState({backendStatus: 'ok'}))
      .catch(() => this.setState({backendStatus: 'error'}))
  }

  onMenuItemClick = selectedMenuItem => this.setState({selectedMenuItem});

  currentView = () => {
    let selectedView;

    if (this.state.backendStatus === 'pending') {
      return <Spinner/>;
    } else if (this.state.backendStatus === 'error') {
      return <ServerError/>;
    }

    switch (this.state.selectedMenuItem) {
      case 'alerts':
        selectedView = <EntriesTable/>;
        break;
      case 'messages':
        selectedView = <MessagesTable/>;
        break;
      case 'about':
        selectedView = <p>About</p>;
        break;
      default:
        selectedView = <p>Not yet implemented</p>;
    }
    return selectedView;
  };

  render() {
    const {classes} = this.props;

    let selectedView = this.currentView();
    return (
      <div className={classes.root}>
        <AppBar position="absolute" className={classes.appBar}>
          <Toolbar>
            <IconButton color="inherit" aria-label="Menu">
              <TrainIcon/>
            </IconButton>
            <Typography variant="title" color="inherit">
              SmsAlerts
            </Typography>
          </Toolbar>
        </AppBar>
        <Drawer
          variant="permanent"
          classes={{
            paper: classes.drawerPaper,
          }}
        >
          <div className={classes.toolbar}/>
          <List>
            <Menu
              selectedMenuItem={this.state.selectedMenuItem}
              onMenuItemClick={this.onMenuItemClick}/>
          </List>
        </Drawer>
        <main className={classes.content} style={{marginTop: 50}}>
          {selectedView}
        </main>
      </div>
    );
  }
}

export default withStyles(styles)(App);