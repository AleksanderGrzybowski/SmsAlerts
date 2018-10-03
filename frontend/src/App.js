import React, { Component } from 'react';
import './App.css';
import axios from 'axios';
import AppBar from '@material-ui/core/AppBar/AppBar';
import Toolbar from '@material-ui/core/Toolbar/Toolbar';
import Typography from '@material-ui/core/Typography/Typography';
import Grid from '@material-ui/core/Grid/Grid';
import Menu from './Menu';
import SimpleTable from './EntriesTable';
import TrainIcon from '@material-ui/icons/Train';
import IconButton from '@material-ui/core/IconButton/IconButton';

class App extends Component {

  constructor(props) {
    super(props);

    this.state = {
      entries: [], messages: [],
      selectedMenuItem: 'alerts'
    };
  }

  componentDidMount() {
    axios.get("/api/data")
      .then(({data}) => this.setState({entries: data.entries, messages: data.messages}));
  }

  onMenuItemClick = selectedMenuItem => this.setState({selectedMenuItem});

  render() {
    return (
      <div className="App">
        <Grid container>
          <Grid item xs={12}>
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
            <SimpleTable entries={this.state.entries}/>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default App;
