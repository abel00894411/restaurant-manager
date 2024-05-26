import { useState } from 'react';
import { menu } from '../models/Menu';
import './Menu.css';

const currencyFormatter = new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 });
const categoryColors = ['#e74c3c', '#f39c12', '#3498db', '#1abc9c']

const Menu = () => {
    const [currentCategory, setCurrentCategory] = useState(menu.getAllCategories()[0].categoria);

    const formatCategoryName = (category) => {
        const firstLetter = category[0].toUpperCase();
        const rest = category.slice(1, category.length);
        return `${firstLetter}${rest}`;
    }

    return (
        <>
            <div className='menu'>
                <h4 className='menu__category-title'>{formatCategoryName(currentCategory)}</h4>

                <div className="menu__content">
                    <div className="menu__content__items">
                        {menu.getAllItems().map((item, i) => {
                            return ( item.category == currentCategory &&
                                <div className={`menu__content__items__item shadow ${item.description ? 'wide' : ''}`} key={i}>
                                    <img className="menu__content__items__item__image" src={`data:image/jpeg;base64,${item.image}`} />
                                    <div className="menu__content__items__item__details">
                                        <p className="menu__content__items__item__name">{item.name}</p>
                                        <p className="menu__content__items__item__price">{currencyFormatter.format(item.price)}</p>
                                        {!!item.description && <p className='menu__content__items__item__description'>{item.description}</p> }
                                    </div>
                                </div>
                            );
                        })}
                    </div>

                </div>
            </div>

            <div className="menu__content__categories">
                {menu.getAllCategories().map((cat, i) => {
                    return (
                        <button 
                            type='button'
                            className={cat.categoria == currentCategory ? 'menu__content__categories__category-btn--current' : 'menu__content__categories__category-btn'}
                            onClick={() => setCurrentCategory(cat.categoria)}
                            style={{
                                backgroundColor: categoryColors[i % categoryColors.length]
                            }}
                            key={i}
                        >
                            {formatCategoryName(cat.categoria)}
                        </button>
                    );
                })}
            </div>
        </>
    );
};

export default Menu;