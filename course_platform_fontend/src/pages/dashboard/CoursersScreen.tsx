import TableComponent from '../../components/table/Table';
import useTable from '../../hooks/useTable';
import { ResponsePagination } from '../../types/DataPagination';
import { Course } from '../../types/Course';
import TableHeaderC from '../../components/table/TableHeader';
import TableBodyC from '../../components/table/TableBody';
import CourseRow from '../../layout/dashboard/CourseRow';
import TableFooterC from '../../components/table/TableFooter';
import { TablePaginationActions } from '../../components/table/TableFooterActon';

const CoursersScreen = () => {
    const {isLoading,page,rowsPerPage,data,handleChangePage,handleChangeRowsPerPage} = useTable<ResponsePagination<Course>>(
    {
        api: "/courses",
        queryKey: "courses",
        size: 4
    }
    );
    console.log(data);
    
    if(isLoading) return "Loading...";
    return (
        <div>
            <TableComponent >
                <TableHeaderC rows={["123"]}/>
                {data && 
                    <>
                        <TableBodyC rows={data.data} render={(data,index) => <CourseRow key={index} row={data}/>}/>
                        <TableFooterC
                        page={page}
                        rowsPerPage={
                            rowsPerPage
                        }
                        count={data.pagination.totalElements}
                        onPageChange={handleChangePage}
                        handleChangeRowsPerPage={handleChangeRowsPerPage}
                        TablePaginationActions={TablePaginationActions}
                        />
                    </>
                }
                
            </TableComponent>
        </div>
    );
};

export default CoursersScreen;