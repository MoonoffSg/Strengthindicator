package net.moonmod.strength_indicator.textrender;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Equipment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.text.Text;

public class ModRenderText extends MinecraftClient{
    private static int maxDur = 0;
    private static int valDur = 0;
    private static int itemX = 10;
    private static int itemY = 10;
    private static int itemColor = 0xfaf7f7;
    private static int step = 15;
    private static int criticalDur = 40;
    private static boolean isHelmet = true;
    private static boolean isBody = true;
    private static boolean isLeggings = true;
    private static boolean isBoots = true;
    private static boolean isCurrentItem = true;
    private static boolean isShadow = true;
    private static PlayerInventory playerInventory = MinecraftClient.getInstance().player.getInventory();;
    private static ItemStack currentItem;
    private static ItemStack Helmet;
    private static ItemStack Body;
    private static ItemStack Leggings;
    private static ItemStack Boots;
    private static ItemStack LeftHand;

    public ModRenderText(RunArgs args) {
        super(args);
    }


    private static void renderText(Text text, DrawContext matrixStack,ItemStack item,int x,int y, int color,boolean shadow){
        matrixStack.drawText(MinecraftClient.getInstance().textRenderer,text,x,y,color,shadow);
        matrixStack.drawItem(item,0,y-6);
    }

    private static void load(DrawContext matrixStack, RenderTickCounter tickDelta){
        currentItem = MinecraftClient.getInstance().player.getInventory().getStack(playerInventory.selectedSlot);
        maxDur = currentItem.getMaxDamage();
        valDur = maxDur- currentItem.getDamage();
        LeftHand = playerInventory.getStack(40);
        renderArmor(matrixStack);
        if(!currentItem.isEmpty() && isCurrentItem && (currentItem.getItem() instanceof ToolItem || currentItem.getItem() instanceof Equipment)){
            renderText(Text.of(valDur + "/" + maxDur),matrixStack,currentItem,itemX+step,itemY+4*step,itemColor,isShadow);
        }
        if(!LeftHand.isEmpty() && LeftHand.getItem() instanceof ToolItem || LeftHand.getItem() instanceof Equipment){
            maxDur = LeftHand.getMaxDamage();
            valDur = maxDur- LeftHand.getDamage();
            renderText(Text.of(valDur + "/" + maxDur),matrixStack,LeftHand,itemX+step,itemY+5*step,itemColor,isShadow);
        }


    }
    private static void renderArmor(DrawContext matrixStack){
        Helmet = playerInventory.getStack(39);
        Body = playerInventory.getStack(38);;
        Leggings = playerInventory.getStack(37);;
        Boots = playerInventory.getStack(36); ;

        if(!Helmet.isEmpty()){
            matrixStack.drawText(MinecraftClient.getInstance().textRenderer,Text.of(Helmet.getMaxDamage()-Helmet.getDamage() + "/" + Helmet.getMaxDamage()),itemX + step,itemY,itemColor,isShadow);
            matrixStack.drawItem(Helmet,0,itemY-4);
        }
        if(!Body.isEmpty()){
            matrixStack.drawText(MinecraftClient.getInstance().textRenderer,Text.of(Body.getMaxDamage()-Body.getDamage() + "/" + Body.getMaxDamage()),itemX+ step,itemY + step,itemColor,isShadow);
            matrixStack.drawItem(Body,0,itemY-4+ step);
        }
        if(!Leggings.isEmpty()){
            matrixStack.drawText(MinecraftClient.getInstance().textRenderer,Text.of(Leggings.getMaxDamage()-Leggings.getDamage() + "/" + Leggings.getMaxDamage()),itemX+ step,itemY + 2*step,itemColor,isShadow);
            matrixStack.drawItem(Leggings,0,itemY-4 + 2*step);
        }
        if(!Boots.isEmpty()){
            matrixStack.drawText(MinecraftClient.getInstance().textRenderer,Text.of(Boots.getMaxDamage()-Boots.getDamage() + "/" + Boots.getMaxDamage()),itemX+ step,itemY + 3*step,itemColor,isShadow);
            matrixStack.drawItem(Boots,0,itemY-4 + 3*step);
        }

    }
    public static void register(DrawContext matrixStack, RenderTickCounter tickDelta){
                load(matrixStack, tickDelta);
    }
}
