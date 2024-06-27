
import storage from '../service/storage';
import { useDispatch, useSelector } from 'react-redux';
import { state } from '../types/store/state';
import { updateThemeState } from '../store/reducer/ThemeSlice';

const useTheme = () => {
    const {isDarkMode,isExpand} = useSelector((state : state) => state.theme);
    const dispatch = useDispatch();
    
    const handleToggleExpand = () => {
        storage.set("isExpand",!isExpand);
        dispatch(updateThemeState({isExpand: !isExpand}))
    }
    const handleToggleDarkMode = () => {
        storage.set("isDarkMode",!isDarkMode);
        dispatch(updateThemeState({isDarkMode: !isDarkMode}))
    }
    return {
        isDarkMode,
        isExpand,
        handleToggleExpand,
        handleToggleDarkMode
    }
};

export default useTheme;