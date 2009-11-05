package 
{

import flash.display.Sprite;
import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.ListDropIndicator;

[ExcludeClass]

public class _MenuStyle
{
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuCheckEnabled', _line='928')]
    private static var _embed_css_Assets_swf_MenuCheckEnabled_656258742:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuBranchDisabled', _line='925')]
    private static var _embed_css_Assets_swf_MenuBranchDisabled_1018366155:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuSeparator', _line='937')]
    private static var _embed_css_Assets_swf_MenuSeparator_1789694612:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuRadioEnabled', _line='935')]
    private static var _embed_css_Assets_swf_MenuRadioEnabled_1526665133:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuRadioDisabled', _line='936')]
    private static var _embed_css_Assets_swf_MenuRadioDisabled_121339542:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuBranchEnabled', _line='926')]
    private static var _embed_css_Assets_swf_MenuBranchEnabled_1310901570:Class;
    [Embed(_pathsep='true', _resolvedSource='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', source='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$Assets.swf', _file='C:/Program Files/Adobe/Flex Builder 2/Flex SDK 2/frameworks/libs/framework.swc$defaults.css', symbol='MenuCheckDisabled', _line='927')]
    private static var _embed_css_Assets_swf_MenuCheckDisabled_983563565:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
		var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("Menu");
    
        if (!style)
		{
			style = new CSSStyleDeclaration();
			StyleManager.setStyleDeclaration("Menu", style, false);
		}
    
        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "menuBorder";
                this.branchDisabledIcon = _embed_css_Assets_swf_MenuBranchDisabled_1018366155;
                this.branchIcon = _embed_css_Assets_swf_MenuBranchEnabled_1310901570;
                this.checkDisabledIcon = _embed_css_Assets_swf_MenuCheckDisabled_983563565;
                this.checkIcon = _embed_css_Assets_swf_MenuCheckEnabled_656258742;
                this.dropIndicatorSkin = mx.skins.halo.ListDropIndicator;
                this.dropShadowEnabled = true;
                this.paddingBottom = 1;
                this.paddingLeft = 1;
                this.paddingRight = 0;
                this.paddingTop = 1;
                this.radioDisabledIcon = _embed_css_Assets_swf_MenuRadioDisabled_121339542;
                this.radioIcon = _embed_css_Assets_swf_MenuRadioEnabled_1526665133;
                this.separatorSkin = _embed_css_Assets_swf_MenuSeparator_1789694612;
                this.verticalAlign = "middle";
            };
        }
    }
}

}
