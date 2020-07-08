package xm.takeway;

import xm.takeway.control.MGoodsManager;
import xm.takeway.control.MMerchantManager;
import xm.takeway.control.MShoppingCar;
import xm.takeway.control.MUserManager;
import xm.takeway.itf.GoodsManager;
import xm.takeway.itf.MerchantManager;
import xm.takeway.itf.ShoppingCarManager;
import xm.takeway.itf.UserManager;

public class TakeawayUtil {
	public static UserManager userManager = new MUserManager();
	public static MerchantManager merchantManager = new MMerchantManager();
	public static GoodsManager goodsManager = new MGoodsManager();
	public static ShoppingCarManager shoppingCarManager = new MShoppingCar();
}
