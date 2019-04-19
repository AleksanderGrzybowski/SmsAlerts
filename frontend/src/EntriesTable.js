import React, { Component } from 'react';
import Spinner from './static/Spinner';
import { fetchAlerts } from './api';

const pageSize = 5;

class EntriesTable extends Component {

  constructor(props) {
    super(props);

    this.state = {loaded: false, entries: {content: []}}
  }

  componentDidMount() {
    this.fetchData(0);
  }

  fetchData = page => fetchAlerts(page, pageSize)
    .then(({data}) => this.setState({loaded: true, currentPage: page, entries: data}));

  handlePageChange = (_, page) => this.fetchData(page);

  // noinspection HtmlUnknownTarget
  renderRows = () => this.state.entries.content.map(entry =>
    <tr key={entry.id}>
      <td component="th" scope="row">
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

  renderPagination = () =>
    <span
      rowsPerPage={pageSize}
      page={this.state.currentPage}
      count={this.state.entries.totalElements}
      onChangePage={this.handlePageChange}
      rowsPerPageOptions={[pageSize]}
    >here pagination</span>;

  render() {
    if (!this.state.loaded) return <Spinner/>;

    return (
      <table>
        <thead>
          <tr>
            <td>Date</td>
            <td>Alert title</td>
          </tr>
        </thead>
        <tbody>
          {this.renderRows()}
          <tr>
            <td>
              {this.renderPagination()}
            </td>
          </tr>
        </tbody>
      </table>
    );
  }
}

export default EntriesTable;