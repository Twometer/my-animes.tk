export function daysFromNow(days: number): Date {
    let date = new Date();
    date.setDate(date.getDate() + days);
    return date;
}