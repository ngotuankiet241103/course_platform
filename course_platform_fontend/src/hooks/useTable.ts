import React, { useState } from 'react';
import { useQuery} from 'react-query';
import { getMethod } from '../service/api';

function useTable<T>({queryKey,api,size = 5} : {queryKey: string,api: string,size?: number}) {
    const [page,setPage] = useState(0);
    const [rowsPerPage,setRowsPerPage] = useState(size);
    const {isLoading,data} = useQuery<T>({
        queryKey: [queryKey,page,rowsPerPage], 
        queryFn: async () => {
            try {
                const response = await getMethod(`${api}?page=${page}&size=${rowsPerPage}`);
                if(response && response.status){
                    return response.data.result;
                }
            } catch (error) {
                console.log(error);
                
            }
        },
        staleTime: 1000 * 2400
    })
    const handleChangePage = (
        event: React.MouseEvent<HTMLButtonElement> | null,
        newPage: number,
      ) => {
        setPage(newPage);
        
    };
    
    const handleChangeRowsPerPage = (
        event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
      ) => {
       
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };
    return {
        isLoading,
        data,
        page,
        rowsPerPage,
        handleChangePage,
        handleChangeRowsPerPage
    }
}

export default useTable;