package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.ButtonSkin;

[ExcludeClass]

public class _ButtonStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("Button");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("Button", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.textAlign = "center";
                this.cornerRadius = 4;
                this.disabledSkin = mx.skins.halo.ButtonSkin;
                this.downSkin = mx.skins.halo.ButtonSkin;
                this.horizontalGap = 2;
                this.overSkin = mx.skins.halo.ButtonSkin;
                this.paddingLeft = 3;
                this.paddingRight = 3;
                this.selectedDisabledSkin = mx.skins.halo.ButtonSkin;
                this.selectedDownSkin = mx.skins.halo.ButtonSkin;
                this.selectedOverSkin = mx.skins.halo.ButtonSkin;
                this.selectedUpSkin = mx.skins.halo.ButtonSkin;
                this.upSkin = mx.skins.halo.ButtonSkin;
                this.verticalGap = 2;
            };
        }
    }
}

}
