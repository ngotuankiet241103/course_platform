import * as React from 'react';
import Table from '@mui/material/Table';
import TableContainer from '@mui/material/TableContainer';
import Paper from '@mui/material/Paper';

function TableComponent({className,children} : 
  { 
    className?: string,
    children: React.ReactNode
  }
){
 
  return (
    <>
      <div className={`${className}`}>
        <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
           {children}
        
        </Table>
      </TableContainer>
      </div>
    </>
  );
}
export default TableComponent;
