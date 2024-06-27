export type menuItem = {
    path: string,
    item: string
    icon: string,
}

export type Menu = menuItem[];
export type menuList = {
    menu: Menu | []
}