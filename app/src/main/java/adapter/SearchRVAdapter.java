package adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Offer;
import cqut.edu.cn.bangke.R;
import utils.Constants;

/**
 * Created by dhl on 2016/9/11.
 */
public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.SearchViewHolder> {


    OnItemClickListener listener;
    List<Offer> dataList = new ArrayList<>();

    public SearchRVAdapter(List<Offer> dataList) {
        this.dataList = dataList;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchViewHolder viewHolder = null;
        if (viewType == Constants.COMMON_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
            viewHolder = new SearchViewHolder(view);
        } else if (viewType == Constants.FOOT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_more, parent, false);
            viewHolder = new SearchViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < dataList.size()) {
            return Constants.COMMON_TYPE;
        } else
            return Constants.FOOT_TYPE;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    boolean isNoMoreDatas = false;

    public void isNoMoreDatas(boolean is) {
        if (is) {
            this.isNoMoreDatas = is;
            this.notifyDataSetChanged();
        }
    }


    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        if(position < dataList.size())
        holder.bindDatas(dataList.get(position), listener, position);
        else
            holder.setNoMoreDatas(true);
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView sdv_recruit_avatar_search; //头像
        TextView tv_title_search;//标题 如Android开发工程师
        TextView tv_wage_search;//薪资
        TextView tv_comp_name_search;//公司名称
        TextView tv_position_search;//地址
        TextView tv_exp_limit_search;//工作经验
        TextView tv_edu_limit_search;//教育经历
        TextView tv_pub_time_search;//发布时间
        TextView tv_finance_search;//融资信息
        TextView tv_need_search;//招聘人数
        TextView tv_comp_type_search;//公司类型

        ProgressBar pb_loading;//进度条
        TextView tv_loading;//加载提示

        public SearchViewHolder(View itemView) {
            super(itemView);
            sdv_recruit_avatar_search = (SimpleDraweeView) itemView.findViewById(R.id.sdv_recruit_avatar_search);
            tv_title_search = (TextView) itemView.findViewById(R.id.tv_title_search);
            tv_wage_search = (TextView) itemView.findViewById(R.id.tv_wage_search);
            tv_comp_name_search = (TextView) itemView.findViewById(R.id.tv_comp_name_search);
            tv_position_search = (TextView) itemView.findViewById(R.id.tv_position_search);
            tv_exp_limit_search = (TextView) itemView.findViewById(R.id.tv_exp_limit_search);
            tv_edu_limit_search = (TextView) itemView.findViewById(R.id.tv_edu_limit_search);
            tv_pub_time_search = (TextView) itemView.findViewById(R.id.tv_pub_time_search);
            tv_finance_search = (TextView) itemView.findViewById(R.id.tv_finance_search);
            tv_need_search = (TextView) itemView.findViewById(R.id.tv_need_search);
            tv_comp_type_search = (TextView) itemView.findViewById(R.id.tv_comp_type_search);

            pb_loading = (ProgressBar) itemView.findViewById(R.id.pb_load);
            tv_loading = (TextView) itemView.findViewById(R.id.tv_load);
        }


        public void bindDatas(Offer offer, final OnItemClickListener listener, final int position) {
            Uri path = Uri.parse(offer.getAvatar());
            sdv_recruit_avatar_search.setImageURI(path);
            //sdv_recruit_avatar.setImageResource(R.mipmap.ic_feedback);
            tv_title_search.setText(offer.getOffer_name());
            tv_wage_search.setText(offer.getOffer_wage());
            tv_comp_name_search.setText(offer.getComp_name());
            tv_position_search.setText(offer.getPosition());
            tv_exp_limit_search.setText(offer.getExp_limit());
            tv_edu_limit_search.setText(offer.getEdu_limit());
            Date createAt = offer.getCreatedAt();
            //有点莫名其妙
            tv_pub_time_search.setText((createAt.getMonth() + 1) + "月" + createAt.getDate() + "日");
            tv_finance_search.setText(offer.getFinance());
            tv_need_search.setText(offer.getNeed());
            tv_comp_type_search.setText(offer.getComp_type());
            //点击监听
            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.itemClick(position);
                    }
                });
            }
        }

        public void setNoMoreDatas(boolean is){
            if(is){
                pb_loading.setVisibility(View.GONE);
                tv_loading.setText("没有更多了~~");
            } else {
                pb_loading.setVisibility(View.VISIBLE);
                tv_loading.setText("拼命加载中...");
            }
        }
    }

    public interface OnItemClickListener {
        void itemClick(int position);
    }
}

