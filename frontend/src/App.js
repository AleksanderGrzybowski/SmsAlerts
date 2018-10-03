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

    this.state = {data: {entries: []}};
  }

  componentDidMount() {
    axios.get("/api/data").then(({data}) => this.setState({data}));
  }

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
            <Menu/>
          </Grid>

          <Grid item xs={8}>
            <SimpleTable entries={this.state.data.entries}/>
          </Grid>
        </Grid>
      </div>
    );
  }
}

export default App;
