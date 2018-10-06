import React, { Component } from 'react';
import './App.css';
import axios from 'axios';
import Grid from '@material-ui/core/Grid/Grid';
import Menu from './Menu';
import EntriesTable from './EntriesTable';
import MessagesTable from './MessagesTable';
import AppToolbar from './static/AppToolbar';

class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      selectedMenuItem: 'alerts'
    };
  }

  onMenuItemClick = selectedMenuItem => this.setState({selectedMenuItem});

  currentView = () => {
    let selectedView;
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
    let selectedView = this.currentView();

    return (
      <div className="App">
        <Grid container>
          <Grid item xs={12}>
            <AppToolbar/>
          </Grid>
        </Grid>

        <Grid container>
          <Grid item xs={3}>
            <Menu
              selectedMenuItem={this.state.selectedMenuItem}
              onMenuItemClick={this.onMenuItemClick}
            />
          </Grid>

          <Grid item xs={8}>
            {selectedView}
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default App;
