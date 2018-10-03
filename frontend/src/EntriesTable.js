import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

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

function EntriesTable(props) {
  const {classes} = props;

  const rows = props.entries.map(entry =>
    <TableRow key={entry.id}>
      <TableCell component="th" scope="row">
        {entry.publishedDate}
      </TableCell>
      <TableCell>{entry.title}</TableCell>
    </TableRow>
  );

  return (
    <Paper className={classes.root}>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <TableCell>Date</TableCell>
            <TableCell>Alert title</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows}
        </TableBody>
      </Table>
    </Paper>
  );
}

EntriesTable.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(EntriesTable);