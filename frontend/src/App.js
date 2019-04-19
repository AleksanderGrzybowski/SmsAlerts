import React, { Component } from 'react';
import EntriesTable from './EntriesTable';
import MessagesTable from './MessagesTable';
import Menu from './Menu';
import Spinner from './static/Spinner';
import ServerError from './static/ServerError';
import About from './static/About';
import AppToolbar from './static/AppToolbar';
import { healthcheck } from './api';
import 'bootstrap/dist/css/bootstrap.css'


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

    return (
      <div>
        <AppToolbar/>
        <div>
          <div>
            <Menu
              selectedMenuItem={this.state.selectedMenuItem}
              onMenuItemClick={this.onMenuItemClick}
            />
          </div>
        </div>
        <main style={{marginTop: 50}}>
          {this.currentView()}
        </main>
      </div>
    );
  }
}

export default App;