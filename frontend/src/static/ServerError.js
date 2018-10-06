import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
  root: {
    ...theme.mixins.gutters(),
    paddingTop: theme.spacing.unit * 2,
    paddingBottom: theme.spacing.unit * 2,
  },
});

function ServerError(props) {
  const { classes } = props;

  return (
    <div>
      <Paper className={classes.root} elevation={1}>
        <Typography align="center" variant="headline" component="h3" color="error">
          Something went wrong
        </Typography>
        <Typography align="center" component="p">
          Please <a href="/">refresh the page</a> or try again later.
        </Typography>
      </Paper>
    </div>
  );
}

export default withStyles(styles)(ServerError);