export type ResponsePagination<T> = {
    data: T[],
    pagination: {
        page: number,
        totalPage: number,
        size: number,
        totalElements: number
    }
}