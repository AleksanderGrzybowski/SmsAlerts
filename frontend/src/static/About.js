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

function About(props) {
  const {classes} = props;

  return (
    <div>
      <Paper className={classes.root} elevation={1}>
        <Typography align="left" component="p">
          You're welcome to check this project source on <a
            href="https://github.com/AleksanderGrzybowski/SmsAlerts"
            target="_blank"
          >
            GitHub
          </a>.
        </Typography>
      </Paper>
    </div>
  );
}

export default withStyles(styles)(About);