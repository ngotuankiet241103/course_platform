
import Search from '../../components/Search/Search';
import FrameSideBar from './FrameSideBar';
import User from '../../components/User/User';
import { Menu } from '../../types/dashboard/Menu';
import MenuList from './MenuList';
import useTheme from '../../hooks/useTheme';
const menu : Menu = [
    {
        path: `/admin/dashboard`,
        item: "dashboard",
        icon: `fa-solid fa-inbox`
    },
    {
        path: "/admin/coures",
        item: "courses",
        icon: `fa-solid fa-calendar-week`
    },
    {
        path: "/admin/orders",
        item: "orders",
        icon: `fa-regular fa-calendar-days`
    }
]
const Sidebar = () => {
    const {isExpand} = useTheme();
    console.log(isExpand);
    
    return (
        <div className={`w-[300px] `}>
            <div className={`max-sm:fixed bg-gray-200 h-[100vh] inset-0`}>
            <FrameSideBar>
            <div className='max-sm:flex justify-end px-2 md:hidden'>
                <span className='text-[18px]'><i className="fa-solid fa-xmark"></i></span>
            </div>
            <div className='mb-4'>
                <User></User>
            </div>
            {/* <div className={`mb-2 flex gap-2 px-2 cursor-pointer  hover:bg-gray-100 transition-all py-2`}>
                    <span className={`w-[22px] h-[22px] flex justify-center items-center  text-white rounded-full`} >
                        <i className="fa-solid fa-plus"></i>
                    </span>
                    <span className={`ont-semibold`}>   
                    </span>
             </div> */}
                <Search></Search>
                <MenuList menu={menu}></MenuList>
               
            </FrameSideBar>
       </div>
        </div>
    );
};

export default Sidebar;