export const removeDiacritics = (value: string) => {
    const arr = value.split(" ");
    const str = arr.length > 1  
                ? `${arr[0].split('')[0]}+${arr[arr.length -1].split('')[0]}`
                : `${arr[0].split('')[0]}`
    return  str;
}