import { TableCell, TableHead, TableRow } from '@mui/material';
import React from 'react';

const TableHeaderC = ({rows} : {rows: string[]}) => {
    return (
        <TableHead>
          <TableRow>
            {rows.length > 0 && rows.map((row,index) => <TableCell key={index}>{row}</TableCell>)}
          </TableRow>
        </TableHead>
    );
};

export default TableHeaderC;