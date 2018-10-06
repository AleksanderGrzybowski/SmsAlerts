import React, { Component } from 'react';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import TablePagination from '@material-ui/core/TablePagination/TablePagination';
import axios from 'axios';

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

  fetchData = (page) => axios.get(`/api/alerts?size=${pageSize}&page=${page}`)
    .then(({data}) => this.setState({loaded: true, currentPage: page, entries: data}));

  handlePageChange = (_, page) => this.fetchData(page);

  render() {
    if (!this.state.loaded) return <p>Spinner...</p>;

    const rows = this.state.entries.content.map(entry =>
      <TableRow key={entry.id}>
        <TableCell component="th" scope="row">
          {entry.publishedDate}
        </TableCell>
        <TableCell>{entry.title}</TableCell>
      </TableRow>
    );
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
            {rows}
            <tr>
              <TablePagination
                rowsPerPage={pageSize}
                page={this.state.currentPage}
                count={this.state.entries.totalElements}
                onChangePage={this.handlePageChange}
                rowsPerPageOptions={[pageSize]}
              />
            </tr>
          </TableBody>
        </Table>
      </Paper>
    );
  }
}

export default withStyles(styles)(EntriesTable);