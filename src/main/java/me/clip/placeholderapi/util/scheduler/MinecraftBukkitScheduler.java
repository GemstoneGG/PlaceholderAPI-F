package me.clip.placeholderapi.util.scheduler;
/*
 * This file is part of PlaceholderAPI
 *
 * PlaceholderAPI
 * Copyright (c) 2015 - 2021 PlaceholderAPI Team
 *
 * PlaceholderAPI free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PlaceholderAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public class MinecraftBukkitScheduler implements MinecraftScheduler {

    /**
     * Schedules a task to be executed synchronously on the server's main thread.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed.
     */
    @Override
    public SchedulerTask runTask(Plugin plugin, Runnable task) {
        return new BukkitSchedulerTask(Bukkit.getScheduler().runTask(plugin, task));
    }

    /**
     * Schedules a task to be executed asynchronously on a separate thread.
     * This method is suitable for handling time-consuming tasks to avoid blocking the main thread.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed.
     */
    @Override
    public SchedulerTask runAsyncTask(Plugin plugin, Runnable task) {
        return new BukkitSchedulerTask(Bukkit.getScheduler().runTaskAsynchronously(plugin, task));
    }

    /**
     * Creates a delayed task that will run the specified `task` after the given `delay`.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed after the delay.
     * @param delay  The delay before the task is executed.
     */
    @Override
    public SchedulerTask createDelayedTask(Plugin plugin, Runnable task, long delay) {
        return new BukkitSchedulerTask(Bukkit.getScheduler().runTaskLater(plugin, task, delay));
    }

    /**
     * Creates a repeating task that will run the specified `task` after an initial `delay`,
     * and then repeatedly execute with the given `period` between executions.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed repeatedly.
     * @param delay  The delay before the first execution.
     * @param period The time between successive executions.
     */
    @Override
    public SchedulerTask createRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        return new BukkitSchedulerTask(Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period));
    }

    /**
     * Schedules an asynchronous delayed task that will run the specified `task` after the given `delay`.
     * The task will be executed on a separate thread, making it suitable for non-blocking operations.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed after the delay.
     * @param delay  The delay before the task is executed.
     */
    @Override
    public SchedulerTask createAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        return new BukkitSchedulerTask(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay));
    }

    /**
     * Schedules an asynchronous repeating task that will run the specified `task` after an initial `delay`,
     * and then repeatedly execute with the given `period` between executions.
     * The task will be executed on a separate thread, making it suitable for non-blocking operations.
     *
     * @param plugin The plugin associated with this task.
     * @param task   The task to be executed repeatedly.
     * @param delay  The delay before the first execution.
     * @param period The time between successive executions.
     */
    @Override
    public SchedulerTask createAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        return new BukkitSchedulerTask(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period));
    }

    /**
     * Cancels all tasks associated with the given `plugin`.
     *
     * @param plugin The plugin whose tasks should be canceled.
     */
    @Override
    public void cancelTasks(Plugin plugin) {
        Bukkit.getScheduler().cancelTasks(plugin);
    }

    /**
     * Gets the scheduler instance for this class.
     * Since this class is already a subclass of {@link MinecraftScheduler},
     * it returns the current instance as the scheduler.
     *
     * @return The scheduler instance for this class (i.e., this).
     */
    @Override
    public MinecraftScheduler getScheduler() {
        return this;
    }

    public static class BukkitSchedulerTask implements SchedulerTask {

        private final BukkitTask bukkitTask;

        public BukkitSchedulerTask(BukkitTask bukkitTask) {
            this.bukkitTask = bukkitTask;
        }

        @Override
        public void cancel() {
            bukkitTask.cancel();
        }
    }
}
