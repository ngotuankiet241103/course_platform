import { TableFooter, TablePagination, TableRow } from "@mui/material";

export type TablePaginationActionsProps = {
  count: number;
  page: number;
  rowsPerPage: number;
  onPageChange: (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number
  ) => void;
};
type FuncTablePagination = {

  handleChangeRowsPerPage: (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => void;
  TablePaginationActions: (
    props: TablePaginationActionsProps
  ) => JSX.Element;
};
function TableFooterC(
  props: TablePaginationActionsProps & FuncTablePagination 
) {
  const {
    count,
    page,
    rowsPerPage,
    onPageChange,
    handleChangeRowsPerPage,
    TablePaginationActions,
  } = props;
  return (
    <TableFooter>
      <TableRow>
        <TablePagination
          rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
          colSpan={5}
          count={count}
          rowsPerPage={rowsPerPage}
          page={page}
          slotProps={{
            select: {
              inputProps: {
                "aria-label": "page",
              },
              native: true
              
            },
          }}
          onPageChange={onPageChange}
          onRowsPerPageChange={handleChangeRowsPerPage}
          ActionsComponent={TablePaginationActions}
        />
      </TableRow>
    </TableFooter>
  );
}

export default TableFooterC;
