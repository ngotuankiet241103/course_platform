import React from 'react';
import { Course } from '../../types/Course';

import TableRow from '@mui/material/TableRow';
import { TableCell } from '@mui/material';

const CourseRow = ({row} : {row: Course}) => {
    return (
        <TableRow  key={row.title} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
                <TableCell component="th" scope="row">
                {row.title}
                </TableCell>
                <TableCell>
                    <img width={120} height={60} src={row.imageCourse} />
                </TableCell>
                <TableCell>{row.totalLearner}</TableCell>
                <TableCell>{row.isFree}</TableCell>
        </TableRow>
    );
};

export default CourseRow;