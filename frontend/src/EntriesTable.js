import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import TablePagination from '@material-ui/core/TablePagination/TablePagination';
import Spinner from './static/Spinner';
import { fetchAlerts } from './api';

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
});

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
    <TableRow key={entry.id}>
      <TableCell component="th" scope="row">
        {entry.publishedDate}
      </TableCell>
      <TableCell>
        <a href={entry.detailsUrl} target="_blank">
          <img src="ks-logo.png" height="10" style={{marginRight: 10}} alt="logo"/>
        </a>
        {entry.title}
      </TableCell>
    </TableRow>
  );

  renderPagination = () =>
    <TablePagination
      rowsPerPage={pageSize}
      page={this.state.currentPage}
      count={this.state.entries.totalElements}
      onChangePage={this.handlePageChange}
      rowsPerPageOptions={[pageSize]}
    />;

  render() {
    if (!this.state.loaded) return <Spinner/>;

    return (
      <Paper className={this.props.classes.root}>
        <Table className={this.props.classes.table}>
          <TableHead>
            <TableRow>
              <TableCell>Date</TableCell>
              <TableCell>Alert title</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {this.renderRows()}
            <tr>
              <td>
                {this.renderPagination()}
              </td>
            </tr>
          </TableBody>
        </Table>
      </Paper>
    );
  }
}

export default withStyles(styles)(EntriesTable);