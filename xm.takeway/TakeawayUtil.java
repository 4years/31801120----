package xm.takeway;

import xm.takeway.control.MCouponManager;
import xm.takeway.control.MGoodsManager;
import xm.takeway.control.MKnightManager;
import xm.takeway.control.MMerchantManager;
import xm.takeway.control.MOrderManager;
import xm.takeway.control.MRootManager;
import xm.takeway.control.MShoppingCar;
import xm.takeway.control.MUserManager;
import xm.takeway.itf.CouponManager;
import xm.takeway.itf.GoodsManager;
import xm.takeway.itf.KnightManager;
import xm.takeway.itf.MerchantManager;
import xm.takeway.itf.OrderManager;
import xm.takeway.itf.RootManager;
import xm.takeway.itf.ShoppingCarManager;
import xm.takeway.itf.UserManager;

public class TakeawayUtil {
	public static UserManager userManager = new MUserManager();
	public static MerchantManager merchantManager = new MMerchantManager();
	public static GoodsManager goodsManager = new MGoodsManager();
	public static ShoppingCarManager shoppingCarManager = new MShoppingCar();
	public static KnightManager knightManager = new MKnightManager();
	public static CouponManager couponManager = new MCouponManager();
	public static RootManager rootManager = new MRootManager();
	public static OrderManager orderManager = new MOrderManager();
}
