import React, { Component } from 'react';
import Spinner from './static/Spinner';
import { fetchAlerts } from './api';
import Table from 'react-bootstrap/Table';


class EntriesTable extends Component {

  constructor(props) {
    super(props);

    this.state = {loaded: false, entries: {content: []}}
  }

  componentDidMount() {
    this.fetchData();
  }

  fetchData = () => fetchAlerts()
    .then(({data}) => this.setState({loaded: true, entries: data}));


  // noinspection HtmlUnknownTarget
  renderRows = () => this.state.entries.map(entry =>
    <tr key={entry.id}>
      <td>
        {entry.publishedDate} &nbsp;
        {entry.scrapeTime}
      </td>
      <td>
        <a href={entry.detailsUrl} target="_blank">
          <img src="ks-logo.png" height="10" style={{marginRight: 10}} alt="logo"/>
        </a>
        {entry.title}
      </td>
    </tr>
  );

  render() {
    if (!this.state.loaded) return <Spinner/>;

    return (
      <Table striped>
        <thead>
        <tr>
          <td>Date</td>
          <td>Alert title</td>
        </tr>
        </thead>
        <tbody>
        {this.renderRows()}
        </tbody>
      </Table>
    );
  }
}

export default EntriesTable;