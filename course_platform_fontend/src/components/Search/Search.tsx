
import IconMenu from '../Icon/IconMenu';

const Search = () => {
    return (
        <div className={`flex gap-4 p-2 items-center}`}>
            <IconMenu icon='fa-solid fa-magnifying-glass'></IconMenu>
            <span>Search</span>
        </div>
    );
};

export default Search;