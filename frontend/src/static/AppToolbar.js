import React from 'react';
import AccountBalance from '../AccountBalance';

function AppToolbar() {
  return (
    <div>
      <div>
        <span>
          train icon
        </span>
        <span>
          SmsAlerts
        </span>
        <span>
          <AccountBalance/>
        </span> 
      </div>
    </div>
  );
}

export default AppToolbar;
