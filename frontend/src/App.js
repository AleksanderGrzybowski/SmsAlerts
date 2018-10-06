import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import List from '@material-ui/core/List';
import EntriesTable from './EntriesTable';
import MessagesTable from './MessagesTable';
import Menu from './Menu';
import Spinner from './static/Spinner';
import ServerError from './static/ServerError';
import About from './static/About';
import AppToolbar from './static/AppToolbar';
import { healthcheck } from './api';

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
    healthcheck()
      .then(() => this.setState({backendStatus: 'ok'}))
      .catch(() => this.setState({backendStatus: 'error'}))
  }

  onMenuItemClick = selectedMenuItem => this.setState({selectedMenuItem});

  currentView = () => {
    if (this.state.backendStatus === 'pending') {
      return <Spinner/>;
    } else if (this.state.backendStatus === 'error') {
      return <ServerError/>;
    }

    switch (this.state.selectedMenuItem) {
      case 'alerts':
        return <EntriesTable/>;
      case 'messages':
        return <MessagesTable/>;
      case 'about':
        return <About/>;
      default:
        return <p>Not yet implemented</p>;
    }
  };

  render() {
    const {classes} = this.props;

    return (
      <div className={classes.root}>
        <AppToolbar/>
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
              onMenuItemClick={this.onMenuItemClick}
            />
          </List>
        </Drawer>
        <main className={classes.content} style={{marginTop: 50}}>
          {this.currentView()}
        </main>
      </div>
    );
  }
}

export default withStyles(styles)(App);