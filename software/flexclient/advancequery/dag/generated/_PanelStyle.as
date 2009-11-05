package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.TitleBackground;

[ExcludeClass]

public class _PanelStyle
{
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='CloseButtonOver', _line='1021')]
    private static var _embed_css_Assets_swf_CloseButtonOver_811269556:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='CloseButtonDisabled', _line='1019')]
    private static var _embed_css_Assets_swf_CloseButtonDisabled_1587231348:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='CloseButtonDown', _line='1020')]
    private static var _embed_css_Assets_swf_CloseButtonDown_1250158246:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='CloseButtonUp', _line='1022')]
    private static var _embed_css_Assets_swf_CloseButtonUp_263728595:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("Panel");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("Panel", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.backgroundColor = 0xffffff;
                this.borderAlpha = 0.4;
                this.borderColor = 0xe2e2e2;
                this.borderStyle = "default";
                this.borderThickness = 0;
                this.borderThicknessLeft = 10;
                this.borderThicknessRight = 10;
                this.borderThicknessTop = 2;
                this.closeButtonDisabledSkin = _embed_css_Assets_swf_CloseButtonDisabled_1587231348;
                this.closeButtonDownSkin = _embed_css_Assets_swf_CloseButtonDown_1250158246;
                this.closeButtonOverSkin = _embed_css_Assets_swf_CloseButtonOver_811269556;
                this.closeButtonUpSkin = _embed_css_Assets_swf_CloseButtonUp_263728595;
                this.cornerRadius = 4;
                this.dropShadowEnabled = true;
                this.paddingBottom = 0;
                this.paddingLeft = 0;
                this.paddingRight = 0;
                this.paddingTop = 0;
                this.resizeEndEffect = "Dissolve";
                this.resizeStartEffect = "Dissolve";
                this.statusStyleName = "windowStatus";
                this.titleBackgroundSkin = mx.skins.halo.TitleBackground;
                this.titleStyleName = "windowStyles";
                var effects:Array = style.mx_internal::effects;
                if (!effects)
                    effects = style.mx_internal::effects = new Array();
                effects.push("resizeEndEffect");
                effects.push("resizeStartEffect");
            };
        }
    }
}

}
