export type Course = {
    title: string,
    description: string,
    imageCourse: string,
    price: number;
    isFree: boolean,
    totalLearner: number,
    detail: {
        [key: string]: string | boolean | number
    } | null
}