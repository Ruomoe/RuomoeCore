package cc.canyi.core.utils;

import com.comphenix.protocol.utility.StreamSerializer;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemUtils {
    /**
     * 判断是否为符合条件物品
     *
     * @param stack     物品
     * @param predicate 条件函数
     * @return 是否符合
     */
//    public static boolean checkItem(ItemStack stack, Predicate<ItemStack> predicate) {
//        return predicate.test(stack);
//    }

    /**
     * 判断玩家背包是否有多余空位
     *
     * @param player 玩家
     * @return 是否有空位
     */
    public static boolean checkInvHasEmptySlot(Player player) {
        PlayerInventory playerInventory = player.getInventory();
        for (int i = 0; i < 36; i++) {
            ItemStack stack = playerInventory.getItem(i);
            if (isNotItem(stack)) return true;
        }
        return false;
    }

    public static int getPlayerInvEmptySlot(Player player) {
        int count = 0;
        for(int i = 0; i < 36; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if(isNotItem(stack)) count ++;
        }
        return count;
    }

    /**
     * 替换玩家背包内所有符合条件的物品
     *
     * @param replaceItem 替换成的物品
     * @param player      玩家
     * @param predicate   替换条件
     * @return 替换了几件
     */
//    public static int replaceItem(ItemStack replaceItem, Player player, Predicate<ItemStack> predicate) {
//        int replaced = 0;
//        PlayerInventory playerInventory = player.getInventory();
//
//        for (int i = 0; i < 36; i++) {
//            ItemStack stack = playerInventory.getItem(i);
//            if (isItem(stack)) {
//                if (predicate.test(stack)) {
//                    playerInventory.setItem(i, replaceItem);
//                    stack.setType(Material.AIR);
//                    replaced++;
//                }
//            }
//        }
//        return replaced;
//    }

    /**
     * 替换玩家背包内符合条件的物品一件
     *
     * @param replaceItem 替换成的物品
     * @param player      玩家
     * @param predicate   替换条件
     * @return 是否有一件被替换成功
     */
//    public static boolean replaceItemOnce(ItemStack replaceItem, Player player, Predicate<ItemStack> predicate) {
//        PlayerInventory playerInventory = player.getInventory();
//        boolean hasReplaced = false;
//        for (int i = 0; i < 36; i++) {
//            ItemStack stack = playerInventory.getItem(i);
//            if (isItem(stack)) {
//                if (predicate.test(stack)) {
//                    playerInventory.setItem(i, replaceItem);
//                    stack.setType(Material.AIR);
//                    hasReplaced = true;
//                    break;
//                }
//            }
//        }
//        return hasReplaced;
//    }
    /**
     * 替换玩家背包内指定格子物品
     *
     * @param replaceItem 替换成的物品
     * @param player      玩家
     * @param slot        格子
     * @return 是否替换成功
     */
    public static boolean replaceItem4Slot(ItemStack replaceItem, Player player, int slot) {
        PlayerInventory playerInventory = player.getInventory();
        playerInventory.setItem(slot, replaceItem);
        return true;
    }

    /**
     * 是否为两个相同的物品
     *
     * @param stack1 物品1
     * @param stack2 物品2
     * @return 是否相同
     */
    public static boolean isSameItem(ItemStack stack1, ItemStack stack2) {
        return stack1.isSimilar(stack1);
    }

    /**
     * 是否为物品
     *
     * @param stack 被判断物品
     * @return 是否是物品 (非空气且非NULL)
     */
    public static boolean isItem(ItemStack stack) {
        return stack != null && !stack.getType().equals(Material.AIR);
    }

    /**
     * 获取玩家背包有多少符合条件的物品
     *
     * @param player    玩家
     * @param predicate 条件
     * @return 符合条件的物品数量
     */
//    public static int getPlayerInvSomeItemHasNumber(Player player, Predicate<ItemStack> predicate) {
//        int count = 0;
//        PlayerInventory playerInventory = player.getInventory();
//        for (int i = 0; i < 36; i++) {
//            ItemStack stack = playerInventory.getItem(i);
//            if (isItem(stack) && predicate.test(stack)) count += stack.getAmount();
//        }
//        return count;
//    }

    /**
     * 移除玩家背包指定数量符合条件的物品
     *
     * @param player    玩家
     * @param predicate 条件
     * @param number    数量
     * @return 是否移除成功
     */
//    public static boolean removeSomeItemFormPlayerInv(Player player, Predicate<ItemStack> predicate, int number) {
//        int sum = getPlayerInvSomeItemHasNumber(player, predicate);
//        if (sum >= number) {
//            return removeSomeItemFormPlayerInvNotCheckHas(player, predicate, number);
////            int count = 0;
////            PlayerInventory inventory = player.getInventory();
////            for (int i = 0; i < 36; i++) {
////                if (count == sum) break;
////                ItemStack stack = inventory.getItem(i);
////                if (isItem(stack) && predicate.test(stack)) {
////                    int stackAmount = stack.getAmount();
////                    if (count + stackAmount > number) {
////                        //多了
////                        int removeAmount = number - count;
////                        stack.setAmount(stackAmount - removeAmount);
////                        inventory.setItem(i, stack);
////                        count += removeAmount;
////                    } else if (count + stackAmount == number) {
////                        //正好
////                        stack.setType(Material.AIR);
////                        inventory.setItem(i, null);
////                        count += stackAmount;
////                    } else {
////                        //不够
////                        stack.setType(Material.AIR);
////                        inventory.setItem(i, null);
////                        count += stackAmount;
////                    }
////                }
////            }
////            return count == sum;
//        } else return false;
//    }

    /**
     * 移除玩家背包指定数量符合条件的物品 不检查玩家是否有这些物品
     *
     * @param player    玩家
     * @param predicate 条件
     * @param number    数量
     * @return 是否移除成功
     */
//    public static boolean removeSomeItemFormPlayerInvNotCheckHas(Player player, Predicate<ItemStack> predicate, int number) {
//        int count = 0;
//        PlayerInventory inventory = player.getInventory();
//        for (int i = 0; i < 36; i++) {
//            if (count == number) break;
//            ItemStack stack = inventory.getItem(i);
//            if (isItem(stack) && predicate.test(stack)) {
//                int stackAmount = stack.getAmount();
//                if (count + stackAmount > number) {
//                    //多了
//                    int removeAmount = number - count;
//                    stack.setAmount(stackAmount - removeAmount);
//                    inventory.setItem(i, stack);
//                    count += removeAmount;
//                } else if (count + stackAmount == number) {
//                    //正好
//                    stack.setType(Material.AIR);
//                    inventory.setItem(i, null);
//                    count += stackAmount;
//                } else {
//                    //不够
//                    stack.setType(Material.AIR);
//                    inventory.setItem(i, null);
//                    count += stackAmount;
//                }
//            }
//        }
//        return count == number;
//    }

    /**
     * 从玩家背包中拿走指定名字物品多少个 （只能最多拿64个）
     * @param player 玩家
     * @param needItemName 需要的物品名字
     * @param amount 数量
     * @return 是否成功
     */
    public static boolean removeItemFromPlayerInvByItemName(Player player, String needItemName, int amount) {
        PlayerInventory playerInventory = player.getInventory();
        int sum = 0;
        for(int i = 0; i < playerInventory.getSize(); i++) {
            ItemStack stack = playerInventory.getItem(i);
            if(isItem(stack) && stack.getItemMeta().hasDisplayName()) {
                String itemName = stack.getItemMeta().getDisplayName();
                if(itemName.equals(needItemName)) {
                    if(sum + stack.getAmount() > amount) {
                        //足够 直接减少数量
                        int removeAmount = amount - sum;
                        stack.setAmount(stack.getAmount() - removeAmount);
                        playerInventory.setItem(i, stack);
                        sum += removeAmount;
                        break;
                    }else if(sum + stack.getAmount() == amount) {
                        //足够 设置为null
                        sum += stack.getAmount();
                        stack.setType(Material.AIR);
                        playerInventory.setItem(i, stack);
                        break;
                    }else {
                        //不够
                        sum += stack.getAmount();
                        stack.setType(Material.AIR);
                        playerInventory.setItem(i, stack);
                    }
                }
            }
        }
        return sum == amount;
    }

    /**
     * 是否不是物品
     *
     * @param stack 被判断物品
     * @return 是否不是物品 (是空气或NULL)
     */
    public static boolean isNotItem(ItemStack stack) {
        return stack == null || stack.getType().equals(Material.AIR);
    }

    /**
     * 改变物品
     * @param stack 要改变的物品
     * @param stackConsumer 改变条件
     * @return 改变后物品
     */
//    public static ItemStack changeItem(ItemStack stack, Consumer<ItemStack> stackConsumer){
//        stackConsumer.accept(stack);
//        return stack;
//    }

    /**
     * ProtocolLib 序列化 转物品
     * @param protocolLibData 序列化字符串
     * @return 物品
     */
    @SneakyThrows
    public static ItemStack protocolLibData2Item(String protocolLibData) {
        StreamSerializer ss = new StreamSerializer();
        return ss.deserializeItemStack(protocolLibData);
    }

    /**
     * ProtocolLib 序列化 转字符串
     * @param stack 物品
     * @return 字符串
     */
    @SneakyThrows
    public static String protocolLibItem2Data(ItemStack stack) {
        StreamSerializer ss = new StreamSerializer();
        return ss.serializeItemStack(stack);
    }


}
