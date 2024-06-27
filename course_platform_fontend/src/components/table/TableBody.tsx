import { TableBody } from '@mui/material';
function TableBodyC<T>({rows,render} : {rows: T[],render: (data: T,index: number) => JSX.Element}) {
    return (
        <TableBody>
          {rows.length > 0 &&  rows.map(render)}
        </TableBody>
    );
}

export default TableBodyC;